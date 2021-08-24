package com.seckill.service.impl;

import com.seckill.entity.Goods;
import com.seckill.entity.SeckillGoods;
import com.seckill.mapper.GoodsMapper;
import com.seckill.service.GoodService;
import com.seckill.vo.GoodsVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    //乐观锁重试次数
    private static final int MAX_RETRIES = 5;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    RedisTemplate redisTemplate;

    public List<GoodsVo> getGoodsList() {
        List<GoodsVo> goodsList = null;
        //简单解决一个缓存击穿，当大量请求来，先查goodsAkey，如果查不到，说明缓存在更新
        // ，此时goodsB肯定已经更新好，如果两个都没，那就查数据库
        goodsList = (List<GoodsVo>) redisTemplate.opsForList().index("goodsA", 0);
        if(goodsList == null || goodsList.size() == 0) {
            goodsList = (List<GoodsVo>) redisTemplate.opsForList().index("goodsB",0);
        }

        if(!goodsList.isEmpty())
            return goodsList;
        else
            return goodsMapper.getGoodsList();
    }

    /**
     * 采用异步定时任务更新商品列表缓存，先更新keyB在更新keyA
     */
    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void RefreshRedis()  {
        List<GoodsVo> goodsList = goodsMapper.getGoodsList();
        if(goodsList == null || goodsList.size() == 0)
            return;

        redisTemplate.delete("goodsB");
        redisTemplate.opsForList().leftPush("goodsB",goodsList);

        redisTemplate.delete("goodsA");
        redisTemplate.opsForList().leftPush("goodsA",goodsList);
    }

    public GoodsVo getGoodsVoByGoodId(Long id) {
        return goodsMapper.getGoodsVoByGoodId(id);
    }

    /**
     *扣减库存,cas+乐观锁解决高并发不同用抢购场景
     */
    @GlobalTransactional
    public boolean reduceStock(GoodsVo goodsVo) {
        //当num小于MAX_RETRIES,判断version是否一致，一致修改，不一致num自增，尝试判断5次
        int num = 0;
        //标记
        Integer flag = 0;
        SeckillGoods skGoods = new SeckillGoods();
        skGoods.setGoodsId(goodsVo.getId());
        skGoods.setVersion(goodsVo.getVersion());

        while(num < MAX_RETRIES) {
            try {
                skGoods.setVersion(goodsMapper.getVersionByGoodsId(goodsVo.getId()));
                flag = goodsMapper.reduceStockByVersion(skGoods);
            }catch (Exception e) {
                e.printStackTrace();
            }
            if(flag != 0)
                break;
            num++;
        }
        return flag > 0;
    }
}


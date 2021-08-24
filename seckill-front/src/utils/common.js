export default {
  // 拼接字符串方法
  splicingStr(str1,str2) {
    return str1+str2
  },
  // 计算秒杀倒计时
  formatTime(startDate,endDate) {
    startDate = new Date(startDate)
    endDate = new Date(endDate)
    startDate = startDate.getTime()
    endDate = endDate.getTime();

    var total = (endDate - startDate)/1000;
    var day = parseInt(total / (24*60*60));//计算整数天数
    var afterDay = total - day*24*60*60;//取得算出天数后剩余的秒数
    var hour = parseInt(afterDay/(60*60));//计算整数小时数
    var afterHour = total - day*24*60*60 - hour*60*60;//取得算出小时数后剩余的秒数
    var min = parseInt(afterHour/60);//计算整数分
    var s = total - day*24*60*60 - hour*60*60 - min*60;//取得算出分后剩余的秒数
    s = parseInt(s)
    day = day < 10 ? '0' + day : day
    hour = hour < 10 ? '0' + hour : hour
    min = min < 10 ? '0' + min : min
    s = s < 10 ? '0' + s : s
    return day+':'+hour+':'+min+':'+s
  }
}
let websocket = null;
if ( typeof WebSocket != 'undefined'){
    /*ws://  websocket
    * 地址端口号+后台定义的server-servlet-context-path+webSocket服务路径*/
    websocket = new WebSocket('ws://localhost:8002/webSocket');
}else {
    alert('该浏览器不支持websocket')
}
websocket.onopen = function (event) {
    console.log('建立连接')
}
websocket.onclose = function (event) {
    console.log('关闭连接')
}

websocket.onerror = function (event) {
  /*  alert('websocket通信发生错误')*/
}
websocket.onbeforeunload = function (event) {
    websocket.close();
}

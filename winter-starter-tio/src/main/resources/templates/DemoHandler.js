var DemoHandler = function () {
    this.onopen = function (event, ws) {
        // ws.send('hello 连上了哦')
        document.getElementById('contentId').innerHTML += 'hello 连上了哦<br>';
    }

    /**
     * 收到服务器发来的消息
     * @param {*} event
     * @param {*} ws
     */
    this.onmessage = function (event, ws) {
        var result = JSON.parse(event.data)
        var time = new Date(result.timestamp)
        var date = time.getFullYear() + '-' + time.getMonth() + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + time.getSeconds();
        var data = '(' + date + ") :" + result.from + " 对 " + result.to + "说: " + result.msg
        document.getElementById('contentId').innerHTML += data + '<br>'
    }

    this.onclose = function (e, ws) {
        // error(e, ws)
    }

    this.onerror = function (e, ws) {
        // error(e, ws)
    }

    /**
     * 发送心跳，本框架会自动定时调用该方法，请在该方法中发送心跳
     * @param {*} ws
     */
    this.ping = function (ws) {
        // log("发心跳了")
        ws.send('{"action": 9, "msg": "心跳内容"}')
    }
}

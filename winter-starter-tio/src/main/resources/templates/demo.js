// var ws_protocol = 'ws'; // ws 或 wss
// var ip = '127.0.0.1'
// var port = 9091

var heartbeatTimeout = 60000; // 心跳超时时间，单位：毫秒
var reconnInterval = 1000; // 重连间隔时间，单位：毫秒

var binaryType = 'blob'; // 'blob' or 'arraybuffer';//arraybuffer是字节
var handler = new DemoHandler()

var tiows

function initWs () {
    // var queryString = 'name=kebi&name=kuli&name=kuli&token=11&userid=adfadsf'
    // var param = "token=11&userid=adfadsf";
    var ws_protocol = document.getElementById('protocol').value
    var ip = document.getElementById('ip').value
    var port = document.getElementById('port').value
    var queryString = document.getElementById('queryString').value
    var param = ''
    tiows = new tio.ws(ws_protocol, ip, port, queryString, param, handler, heartbeatTimeout, reconnInterval, binaryType)
    tiows.connect()
}



function send () {
    var msg = document.getElementById('textId')
    var data = {
        action: 3,
        msg: msg.value,
        to: document.getElementById('toUser').value
    }
    tiows.send(JSON.stringify(data))
}

function sendGroup () {
    var msg = document.getElementById('textId')
    var data = {
        action: 4,
        msg: msg.value,
        to: document.getElementById('toGroup').value
    }
    tiows.send(JSON.stringify(data))
}

function clearMsg () {
    document.getElementById('contentId').innerHTML = ''
}

// initWs()

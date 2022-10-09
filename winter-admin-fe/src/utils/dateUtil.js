import moment from "moment";

function formatDateTime(ms) {
    return moment(ms).format('yyyy-MM-DD HH:mm:ss')
}

export {
    formatDateTime
}
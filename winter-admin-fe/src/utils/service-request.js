import {request} from "@/utils/request";

function serviceRequest(path, method, params, config) {
    return request(process.env.VUE_APP_API_BASE_URL + path, method, params, config)
}

export {
    serviceRequest
}
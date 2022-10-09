import { serviceRequest } from './service-request'

declare module 'vue/types/vue' {
    interface Vue {
        $serviceRequest: serviceRequest;
    }
}

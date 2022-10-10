import {AxiosResponse} from "axios";

declare module 'vue/types/vue' {
    interface Vue {
        $serviceRequest: (path: string, method: string, params?, config?) => Promise<AxiosResponse>;
    }
}

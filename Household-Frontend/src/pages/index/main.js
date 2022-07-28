import { createApp, h } from "vue";
import Index from './index'

const app = createApp({
    render: ()=>h(Index)
});

app.mount("#app");
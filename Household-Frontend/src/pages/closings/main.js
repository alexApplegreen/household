import { createApp, h } from "vue";
import Closings from './closings'

const app = createApp({
    render: ()=>h(Closings)
});

app.mount("#app");
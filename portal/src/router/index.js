import { createRouter, createWebHistory } from 'vue-router';
import WelcomeItem from '../components/ComponentsCards.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: WelcomeItem,
    },
  ],
});

export default router;

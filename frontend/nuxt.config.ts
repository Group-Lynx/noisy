// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  ssr: false,
  devtools: { enabled: true },
  modules: [
    "nuxt-primevue",
    "@nuxtjs/tailwindcss",
    "@formkit/auto-animate/nuxt",
    "@vueuse/nuxt",
    "nuxt-api-party",
  ],
  primevue: {
    usePrimeVue: true,
    options: {
      unstyled: false,
      ripple: true,
    },
    cssLayerOrder: "tailwind-base, primevue, tailwind-utilities",
  },
  css: [
    "assets/css/tailwind.css",
    "primevue/resources/themes/lara-dark-blue/theme.css",
    "primeicons/primeicons.css",
  ],
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {},
    },
  },
  imports: {
    dirs: ["types"],
  },
  runtimeConfig: {
    public: {
      restApiUrl: "http://localhost:8080",
      socketEndpoint: "ws://localhost:8080/socket",
    },
  },
  apiParty: {
    client: "allow",
    endpoints: {
      apiServer: {
        url: "http://localhost:8080",
        cookies: true,
      },
    },
  },
});

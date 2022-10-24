<template>
  <v-app>
    <v-card>
      <v-layout>
        <v-app-bar color="primary" prominent>
          <v-app-bar-nav-icon
            variant="text"
            @click.stop="drawer = !drawer"
          ></v-app-bar-nav-icon>

          <v-toolbar-title>Kubernetes dashboard</v-toolbar-title>

          <v-spacer></v-spacer>
        </v-app-bar>

        <v-navigation-drawer v-model="drawer" bottom temporary>
          <v-list :items="items"></v-list>
        </v-navigation-drawer>

        <v-main>
          <router-view />
        </v-main>
      </v-layout>
    </v-card>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  data: () => ({
    drawer: false,
    group: null,
    items: [
      {
        title: 'Node',
        value: 'Node',
      },
      {
        title: 'Pod',
        value: 'Pod',
      },
      {
        title: 'Deploy',
        value: 'Deploy',
      },
      {
        title: 'StatefulSet',
        value: 'StatefulSet',
      },
      {
        title: 'ServiceAccount',
        value: 'ServiceAccount',
      },
      {
        title: 'CRD',
        value: 'CRD',
      },
    ],
  }),

  watch: {
    group() {
      this.drawer = false
    },
  },
};
</script>

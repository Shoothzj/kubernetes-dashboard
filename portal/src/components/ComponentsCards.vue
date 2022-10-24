<template>
  <div>
    <h1 style="text-align: center">Welcome to Kubernetes dashboard</h1>
  </div>
  <v-container>
    <v-row no-gutters>
      <v-col>
        <v-sheet>
          <v-btn style="width: 300px; height: 200px" block color="secondary"> Deploy</v-btn>
        </v-sheet>
      </v-col>
      <v-col>
        <v-sheet>
          <v-btn style="width: 300px; height: 200px" block color="secondary"> Node</v-btn>
        </v-sheet>
      </v-col>
      <v-col>
        <v-sheet>
          <v-btn style="width: 300px; height: 200px" block color="secondary"> StatefulSet</v-btn>
        </v-sheet>
      </v-col>
    </v-row>
    <v-row no-gutters>
      <v-col>
        <v-sheet>
          <v-btn style="width: 300px; height: 200px" block color="secondary"> ServiceAccount</v-btn>
        </v-sheet>
      </v-col>
      <v-col>
        <v-sheet>
          <v-btn style="width: 300px; height: 200px" block color="secondary"> Pod</v-btn>
        </v-sheet>
      </v-col>
      <v-col>
        <v-sheet>
          <v-btn style="width: 300px; height: 200px" block color="secondary"> Crd</v-btn>
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>

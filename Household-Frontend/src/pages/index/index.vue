<template>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <div
      id="app"
      class="app-template"
  >
    <div class="container">
      <h1>
        Household Planner!
      </h1>
      <h2>
        <a
          class="surely-not-a-link"
          href="https://www.youtube.com/watch?v=nKKkxjH3vew"
        >
          Skrrrt Skrrrt
        </a>
      </h2>
      <button
          @click="to_list()"
          class="btn btn-dark navigation-button"
      >
        Zur aktuellen Einkaufsliste
      </button>
      <button
          @click="to_closings()"
          class="btn btn-dark navigation-button"
      >
        Zur Liste der Abschlüsse
      </button>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Index',
    data() {
      return {

      };
    },
    methods: {
      to_list() {
        window.location.href = '/to_list';
      },

      to_closings() {
        window.location.href = '/to_closings';
      }
    }
  }
</script>

<style>
  @import "../../../static/css/bootstrap.css";

  body {
    background: url(../../../static/images/background_stars_low_res.jpg) no-repeat center center fixed;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
  }

  .container {
    margin-top: 50px;
    padding: 20px;
    background-color: rgba(255, 255, 255, 0.5);
    margin-left: auto;
    margin-right: auto;
    text-align: center;
    border-radius: 20px;
  }

  .surely-not-a-link {
    color: #212529;
    cursor: auto !important;
  }

  .surely-not-a-link:hover {
    color: black;
    cursor: auto !important;
    text-decoration: unset !important;
  }
  .navigation-button {
    margin-top: 5px;
    margin-left: 4px;
    width: 250px;
  }
</style>
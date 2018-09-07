module.exports = function (grunt) {
  'use strict';

  // Load all of the grunt tasks (matching the `grunt-*` pattern) in package.json::devDependencies
  require('load-grunt-tasks')(grunt);

  // Configure Tasks.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

    // Define paths.
    project: {
      app: 'webapp',
      docs: 'docs',
      test: 'test',
      report: 'test/report'
    },

    // Unit testing.
    karma: {
      unit: {
        configFile: 'karma.conf.js',

        // Shut down the Karma server when testing is complete.
        singleRun: true
      }
    },

    // Test coverage (using Istanbul).
    coverage: {
      default: {
        options: {
          thresholds: {
            'statements': 0,
            'branches': 0,
            'lines': 0,
            'functions': 0
          },
          dir: 'coverage',
          root: '<%= project.report %>',
          report: true
        }
      }
    }
	
  });

  // Default.
  grunt.registerTask('default', ['karma']);

  // Generate documentation.
  grunt.registerTask('docs', [
    'ngdocs'
  ]);

  // Report generator.
  grunt.registerTask('report', [
    'karma',
    'coverage'
  ]);
};
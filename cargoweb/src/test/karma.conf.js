// Karma configuration
// Generated on Thu Feb 18 2016 19:14:25 GMT-0500 (Eastern Standard Time)

module.exports = function(config) {
  config.set({

    type: "json",

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: 'webapp/modules',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],

    // optionally, configure the reporter
    coverageReporter: {
      type: 'json',
      dir: '../../test/report/coverage' // For the grunt tasks test directory
    },


    // list of files / patterns to load in the browser
    files: [
        // Scripts called from index.html
        "../modules/js/jquery.js",
        "../modules/js/jquery-ui-1.8.13.custom.min.js",
        "../modules/js/ui.dropdownchecklist-1.4-min.js",
        "../modules/js/moment.js",
        "../modules/js/moment-timezone-with-data.min.js",
        "../modules/js/bootstrap.js",
        "../modules/js/angular.js",
        "../modules/js/angular-file-upload.js",
        "../modules/js/angular-route.js",
        "../modules/js/ui-bootstrap-tpls-0.13.0.js",
        "../modules/js/bootstrap-datetimepicker.js",
        "../modules/js/bootstrap-datepicker.js",
        "../modules/js/bootstrap-select.js",
        "../modules/js/export-csv.js",
        "../modules/app.js",

        "../modules/js/ng-table.js",
           
        "../modules/js/angular-sanitize.js",  
        "../modules/js/select.js", 
        "../modules/js/bindonce.js", 
        "../modules/js/ng-infinite-scroll.min.js",
        "../modules/js/ngstorage.js",
        "../modules/js/bootstrap-multiselect.js",
        "../modules/js/jstz-1.0.4.min.js",

        "../modules/home/home_controller.js",
        "../modules/home/homepage_factory.js",
        "../modules/capacity/paramdefault_controller.js",
        "../modules/capacity/paramdefault_factory.js",
        "../modules/referenceData/managerefdata_ctrl.js",
        "../modules/referenceData/managerefdata_factory.js",
        "../modules/facility/airport-search_controller.js",
        "../modules/facility/airport-search_factory.js",
        "../modules/facility/add_cargo_facility_ctrl.js",
        "../modules/usergroup/searchGroupController.js",
        "../modules/usergroup/searchGroupService.js",
        "../modules/usergroup/searchGroupDirective.js",
        "../modules/HandlingTimeParamsRule/handling_time_params_rule_controller.js",
        "../modules/HandlingTimeParamsRule/handling_time_params_rule_services.js",
        "../modules/interlineflightparams/interline_flight_params_controller.js",
        "../modules/interlineflightparams/interline_flight_params_services.js",

        "../modules/flightcapacity/flight_capacity_param_controller.js",
        "../modules/flightcapacity/flight_capacity_param_services.js",
        "../modules/restrictions/managerestrictionsdefault_controller.js",
        "../modules/routes/routesdefault_controller.js",
        "../modules/routes/manageroutesdefault_controller.js",
        "../modules/routes/routesdefault_factory.js",
        "../modules/negativespav/managenegativespavdefault_controller.js",
        "../modules/negativespav/managenegativespavdefault_factory.js",
        "../modules/awb/manageawbsearchdefault_controller.js",
        "../modules/awb/manageawbsearchdefault_factory.js",
        "../modules/awb/manageawbresearchdefault_controller.js",
        "../modules/awb/manageawbresearchdefault_factory.js",

        "../modules/utilities/utilities_controller.js",
        "../modules/utilities/utilities_factory.js",
        "../modules/utilities/utilities_directive.js",
        "../modules/utilities/cometdController.js",

        "../modules/customeraccount/generaldefault_controller.js",
        "../modules/customeraccount/generaldefault_factory.js",
        "../modules/customeraccount/customerprofiledefault_controller.js",
        "../modules/customeraccount/customerprofiledefault_factory.js",

        "../modules/usergroup/temporaryAssignmentController.js",
        "../modules/usergroup/temporaryAssignmentService.js",
        "../modules/manageawb/manageawbdefault_controller.js",
        "../modules/manageawb/manageawbdefault_factory.js",
        "../modules/manageawb/fromshipperctrl.js",
        "../modules/manageawb/toconsignee_directive.js",
        "../modules/manageawb/toconsignee_service.js",
        "../modules/manageawb/comments_directive.js",
        "../modules/manageawb/signature_directive.js",
        "../modules/manageawb/documents_directive.js",
        "../modules/manageawb/newIdentificationVerification_Controller.js",
        "../modules/manageawb/airWaybillDataService.js",
        "../modules/manageawb/AWBHistoryController.js",

        "../modules/manageawb/Model/routingLotModel.js",
        "../modules/manageawb/Model/routingLineModel.js",
        "../modules/manageawb/Model/routingFlightLegModel.js",
        "../modules/manageawb/Model/routingPieceModel.js",
        "../modules/manageawb/Model/getRouteReqModel.js",
        "../modules/manageawb/Model/getRouteFlightLegModel.js",
        "../modules/manageawb/Model/validateRouteReqModel.js",
        "../modules/manageawb/Model/validateRouteFlightLegModel.js",
        "../modules/manageawb/searchAwbController.js",
        "../modules/manageawb/searchAwbService.js",
        "../modules/manageawb/routing_directive.js",
        "../modules/manageawb/errorhandling_service.js",
        "../modules/manageawb/Model/shipmentModel.js",
        "../modules/manageawb/Model/shipmentLineModel.js",

        "../modules/manageawb/cargoScreening_controller.js",
        "../modules/manageawb/cargoScreening_directive.js",
        "../modules/manageawb/shipmentdetails_directive.js",
        "../modules/manageawb/shipmentReleaseForm_Controller.js",

        "../modules/auditlog/UserActivityLogController.js",
        "../modules/auditlog/UserActivityLogService.js",
        "../modules/manageawb/lotlabels_directive.js",
        "../modules/manageawb/AuthForcedBookingsdefault_controller.js",
        "../modules/manageawb/AuthForcedBookingsdefault_factory.js",
        "../modules/shipmentmgmt/shipmentmgmt_factory.js",
        "../modules/shipmentmgmt/inboundShipment_Controller.js",
        "../modules/shipmentmgmt/transferShipment_Controller.js",
        "../modules/shipmentmgmt/outboundShipment_Controller.js",
        "../modules/shipmentmgmt/deliveredShipment_Controller.js",
        "../modules/shipmentmgmt/missingShipment_Controller.js",

        "../modules/fit/managefitdefault_controller.js",
        "../modules/fit/managefitdefault_factory.js",
        "../modules/manifestsearch/manifestSearchController.js",
        "../modules/manifestsearch/manifestSearchService.js",
        "../modules/commodityCodeDescMapping/commodityCodeDescController.js",
        "../modules/commodityCodeDescMapping/commodityCodeDescService.js",
        "../modules/serviceBucketMapping/serviceLevelBucketMappingController.js",
        "../modules/serviceBucketMapping/serviceLevelBucketMappingService.js",
        "../modules/report/reportsController.js",

        "js/comet/cometd-namespace.js",
        "js/comet/cometd-json.js",
        "js/comet/AckExtension.js",
        "js/comet/TransportRegistry.js",
        "js/comet/Transport.js",
        "js/comet/RequestTransport.js",
        "js/comet/WebSocketTransport.js",
        "js/comet/TimeStampExtension.js",
        "js/comet/ReloadExtension.js",
        "js/comet/TimeSyncExtension.js",
        "js/comet/WebSocketTransport.js",
        "js/comet/CallbackPollingTransport.js",
        "js/comet/LongPollingTransport.js",
        "js/comet/Utils.js",
        "js/comet/Cometd.js",
        "js/comet/jquery.cometd.js",
        "js/comet/jquery.cookie.js",

        "../modules/acsConfig/config.js",
        "../modules/acsConfig/FITConfig.js",
        "../modules/acsConfig/constant.js",
        "../modules/acsConfig/errorconstant.js",
        "../modules/acsConfig/roleconstant.js",
        "../modules/acsConfig/reference_data_constant.js",
        // End of scripts called from index.html

        // Angular plugin for allowing angular components to be tested
        '../../angular-mocks.1.3.5.js',

        // Test Cases files: manageawbdefault_controller.spec.js
        // 'manageawb/manageawbdefault_controller.js',
        // '../../manageawbdefault_controller.spec.js',
        // ,
        'manageawb/routingDirectiveController.js',
        // 'manageawb/routingDirectiveController.spec.js'

        'manageawb/shipmentdetailsDirectiveController.js',
        'manageawb/shipmentdetailsDirectiveController.spec.js',
        'manageawb/routingDirectiveController.FUT.spec.js'
    ],


    // list of files to exclude
    exclude: [
        
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
        'manageawb/routingDirectiveController.js': ['coverage'],
        'manageawb/shipmentdetailsDirectiveController.js': ['coverage']
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress','coverage'], //-- Disable coverage module so that the console.log error lines match the place where the error took place


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}

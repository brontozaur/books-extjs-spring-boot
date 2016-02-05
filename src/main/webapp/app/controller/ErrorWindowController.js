Ext.define('BM.controller.ErrorWindowController', {
    extend: 'Ext.app.Controller',
    views: [
        'ErrorWindow'
    ],

    init: function () {
        this.control({
            'errorwindow button[itemId=detailsBtn]': {
                click: this.showDetails
            },
            'errorwindow button[itemId=closeBtn]': {
                click: this.closeWindow
            }
        });
    },

    showDetails: function (button, clickEvent, options) {
        var errorDetailsField = Ext.ComponentQuery.query('errorwindow textarea[itemId=errorDetails]')[0];
        var parent = errorDetailsField.up('panel');
        var window = button.up('errorwindow');
        if (window.getLayout().getActiveItem().getItemId() == 'errorTitle') {
            window.getLayout().setActiveItem('errorDetails');
        } else {
            window.getLayout().setActiveItem('errorTitle');
        }
    },

    closeWindow: function (button, clickEvent, options) {
        var window = button.up('errorwindow');
        window.close();
    }
});
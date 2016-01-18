Ext.define('BM.controller.ErrorWindowController', {
            extend: 'Ext.app.Controller',
            views: [
                'ErrorWindow'
            ],

            init: function() {
                this.control({
                            'errorwindow button[itemId=detailsBtn]': {
                                click: this.showDetails
                            },
                            'errorwindow button[itemId=closeBtn]': {
                                click: this.closeWindow
                            }
                        });
            },

            showDetails: function(button, clickEvent, options) {
                var errorStackTraceField = Ext.ComponentQuery.query('errorwindow textarea[itemId=errorStackTrace]')[0];
                var parent = errorStackTraceField.up('panel');
                if (!parent.getCollapsed()) {
                    parent.collapse();
                } else {
                    parent.expand();
                }
            },

            closeWindow: function(button, clickEvent, options) {
                var window = button.up('errorwindow');
                window.close();
            }
        });
Ext.define('BM.view.ErrorWindow', {
            extend: 'Ext.window.Window',
            alias: 'widget.errorwindow',
            height: 500,
            width: 900,
            layout: 'border',
            items: [
                {
                    xtype: 'panel',
                    layout: 'hbox',
                    region: 'center',
                    flex: 1,
                    items: [
                        {
                            xtype: 'image',
                            src: 'css/images/error.png',
                            padding: '5 5 5 5'
                        },
                        {
                            xtype: 'label',
                            text: 'A intervenit o eroare.',
                            baseCls: 'app-header',
                            itemId: 'errorName',
                            padding: '10 5 5 30'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    region: 'south',
                    collapseDirection: 'down',
                    collapsible: true,
                    height: 400,
                    flex: 9,
                    layout: 'anchor',
                    items: [
                        {
                            xtype: 'textarea',
                            itemId: 'errorStackTrace',
                            preventScrollbars: false,
                            anchor: '100% 100%'
                        }
                    ]
                }
            ],
            buttons: [
                {
                    text: 'Inchide',
                    itemId: 'closeBtn'
                },
                {
                    text: 'Detalii',
                    itemId: 'detailsBtn'
                }
            ],
            setErrorMessage: function(errorMessage) {
                var errorNameField = Ext.ComponentQuery.query('errorwindow label[itemId=errorName]')[0];
                errorNameField.setText(errorMessage/* .match(/(.{1,40})/g) */);
            },
            setStackTace: function(stackTrace) {
                var errorStackTraceField = Ext.ComponentQuery.query('errorwindow textarea[itemId=errorStackTrace]')[0];
                errorStackTraceField.setValue(stackTrace.split("		"));
            }
        });
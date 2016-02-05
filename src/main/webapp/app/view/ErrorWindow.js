Ext.define('BM.view.ErrorWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.errorwindow',
    height: 500,
    width: 800,
    layout: 'card',
    items: [
        {
            xtype: 'panel',
            layout: 'hbox',
            itemId: 'errorTitle',
            items: [
                {
                    xtype: 'image',
                    src: 'css/images/error.png',
                    padding: '5 5 5 5'
                },
                {
                    xtype: 'label',
                    baseCls: 'app-header',
                    itemId: 'errorName',
                    padding: '10 5 5 30',
                    height: 300
                }
            ]
        },
        {
                xtype: 'textarea',
                itemId: 'errorDetails',
                preventScrollbars: false
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
    setErrorMessage: function (errorMessage) {
        var errorNameField = Ext.ComponentQuery.query('errorwindow label[itemId=errorName]')[0];
        errorNameField.setText(errorMessage/* .match(/(.{1,40})/g) */);
    },
    setErrorDetails: function (details) {
        var detailsField = Ext.ComponentQuery.query('errorwindow textarea[itemId=errorDetails]')[0];
        detailsField.setValue(details);
    }
});
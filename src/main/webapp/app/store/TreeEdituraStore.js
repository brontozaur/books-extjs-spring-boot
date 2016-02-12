Ext.define('BM.store.TreeEdituraStore', {
    extend: 'Ext.data.TreeStore',
    model: 'BM.model.TreeEdituraModel',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: '/editura/tree',
        reader: {
            type: 'json',
            method: 'POST'
        },
        listeners : {
            exception : function(proxy, response, operation) {
                createErrorWindow(response);
            }
        }
    },
    root: {
        expanded: true,
        name: 'Edituri'
    },
    sorters: [
        {
            property: 'treeItemName',
            direction: 'ASC'
        }
    ]
});
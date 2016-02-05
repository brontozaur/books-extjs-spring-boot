Ext.define('BM.store.TreeBooksStore', {
    extend: 'Ext.data.TreeStore',
    model: 'BM.model.TreeBooksModel',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: 'book/tree',
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
        name: 'Carti'
    },
    sorters: [
        {
            property: 'treeItemName',
            direction: 'ASC'
        }
    ]
});
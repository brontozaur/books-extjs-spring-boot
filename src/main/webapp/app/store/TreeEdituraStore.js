Ext.define('BM.store.TreeEdituraStore', {
            extend: 'Ext.data.TreeStore',
            model: 'BM.model.TreeEdituraModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: 'books?event=get-tree-edituri',
                reader: {
                    type: 'json',
                    method: 'POST'
                }
            },
            root: {
                expanded: true,
                name: 'EDITURI'
            },
            sorters: [
                {
                    property: 'treeItemName',
                    direction: 'ASC'
                }
            ]
        });
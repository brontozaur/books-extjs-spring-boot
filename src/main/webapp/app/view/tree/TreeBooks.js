Ext.define('BM.view.tree.TreeBooks', {
            extend: 'Ext.tree.Panel',
            alias: 'widget.treebooks',
            store: 'TreeBooksStore',
            displayField: 'treeItemName',
            rootVisible: true,
            name: 'carti'
        });
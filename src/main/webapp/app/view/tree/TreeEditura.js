Ext.define('BM.view.tree.TreeEditura', {
            extend: 'Ext.tree.Panel',
            alias: 'widget.treeeditura',
            store: 'TreeEdituraStore',
            displayField: 'treeItemName',
            rootVisible: true,
            displayMode: 'default', //possible modes: default and flat
            name: 'edituri'
        });
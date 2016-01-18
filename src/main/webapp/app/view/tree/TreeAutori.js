Ext.define('BM.view.tree.TreeAutori', {
            extend: 'Ext.tree.Panel',
            alias: 'widget.treeautori',
            store: 'TreeAutoriStore',
            displayField: 'treeItemName',
            rootVisible: true,
            displayMode: 'default' //possible modes: default and flat
        });
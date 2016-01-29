Ext.define('BM.view.tree.LeftTreeArea', {
            extend: 'Ext.panel.Panel',
            alias: 'widget.lefttree',
            layout: 'card',
            padding: '0 2 2 2',
            activeItem: 'treeAutori',
            title: 'Grupare dupa autori',
            split: true,
            collapsible: true,
            border: false,
            items: this.buildItems(),
            tools: [
                {
                    type: 'plus',
                    itemId: 'addTool',
                    tooltip: ''
                },
                {
                    type: 'refresh',
                    itemId: 'refreshTool',
                    tooltip: 'Refresh'
                }
            ]
        });

function buildItems() {
    return [
        {
            xtype: 'treeautori',
            itemId: 'treeAutori'
        },
        {
            xtype: 'treebooks',
            itemId: 'treeBooks'
        },
        {
            xtype: 'treeeditura',
            itemId: 'treeEditura'
        }
    ];
}
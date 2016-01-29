Ext.define('BM.controller.TreeEdituraController', {
            extend: 'BM.controller.AbstractLeftTreeAreaController',
            stores: [
                'TreeEdituraStore'
            ],

            views: [
                'tree.TreeEditura'
            ],

            refs: [
                {
                    ref: 'tree',
                    selector: 'treeeditura'
                },
                {
                    ref: 'leftTreeArea',
                    selector: 'lefttree'
                }
            ],

            init: function() {
                var me = this;
                me.control({
                            'treeeditura': {
                                itemclick: this.itemClick,
                                itemcontextmenu: this.itemContextMenu,
                                containercontextmenu: this.showMenu
                            }
                        });
                me.callParent(arguments);
            },

            itemClick: function(tree, recordItem, item, index, e, eOpts) {
                var treeItemValue = recordItem.get('name');
                var isRoot = recordItem.isRoot();
                var grid = Ext.ComponentQuery.query('booksgrid')[0];
                var store = grid.getStore();
                store.clearFilter(true);
                store.filter([
                    {
                        filterFn: function(record) {
                            if (isRoot || Ext.isEmpty(treeItemValue)) {
                                return true;
                            }
                            var numeEditura = record.get('numeEditura');
                            if (!numeEditura){
                            	numeEditura = '';
                            }
                            numeEditura = numeEditura.toLowerCase();
                            return numeEditura.indexOf(treeItemValue.toLowerCase()) === 0; // starts with this letter
                        }
                    }
                ]);
                clearInfoAreaFields();
                enablebuttonsEditura(false);
            },

            add: function() {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    var window = Ext.widget('editurawindow');
                    window.show();
                }
            },

            refreshTree: function() {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    me.refreshTreeInternal();
                }
            }
        });
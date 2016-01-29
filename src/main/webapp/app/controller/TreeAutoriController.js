Ext.define('BM.controller.TreeAutoriController', {
            extend: 'BM.controller.AbstractLeftTreeAreaController',
            stores: [
                'TreeAutoriStore'
            ],

            views: [
                'tree.TreeAutori'
            ],

            refs: [
                {
                    ref: 'tree',
                    selector: 'treeautori'
                },
                {
                    ref: 'leftTreeArea',
                    selector: 'lefttree'
                },
                {
                    ref: 'changeViewButton',
                    selector: 'lefttree tool[itemId=toggleTool]'
                }
            ],

            init: function() {
                var me = this;
                me.control({
                            'treeautori': {
                                beforeload: this.loadParamsToRequest,
                                itemclick: this.itemClick,
                                itemcontextmenu: this.itemContextMenu,
                                containercontextmenu: this.showMenu
                            }
                        });
                me.callParent(arguments);
            },

            loadParamsToRequest: function(store, operation, eOpts) {
                var node = operation.node;
                var displayMode = this.getTree().displayMode;

                Ext.Ajax.request({
                    url: '/autor/tree',
                    method: 'GET',
                    params: {
                        nodeId: node.get('name'),
                        root: node.isRoot(),
                        displayMode: displayMode
                    },
                    scope: this,
                    success: function (result, request) {
                        debugger;
                        //TODO
                    },
                    failure: function (result, request) {
                        createErrorWindow(result);
                    }
                });
                //this will prevent the load of the store
                return false;
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
                            var numeAutor = record.get('authorName');
                            if (!numeAutor){
                                if ("Fara autor" === treeItemValue){
                                    return true;
                                }
                            	numeAutor = '';
                            }
                            numeAutor = numeAutor.toLowerCase();                              
                            return numeAutor.indexOf(treeItemValue.toLowerCase()) === 0; // starts with this letter
                        }
                    }
                ]);
                clearInfoAreaFields();
                enablebuttonsAutor(false);
            },

            add: function() {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    var window = Ext.widget('autorwindow');
                    window.show();
                }
            },

            refreshTree: function() {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    me.refreshTreeInternal();
                }
            },

            changeView: function(toolItem, event, eOpts) {
                var me = this;
                if (me.getTree() === me.getActiveItem()) {
                    me.changeViewInternal();
                }
            }
        });
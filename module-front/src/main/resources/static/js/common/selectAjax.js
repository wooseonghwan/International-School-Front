(function($) {
    'use strict';
    let selectBoxActions = {
        render: function(data) {
            let select = this;
            let _options = this.data('_options');
            let txt = '';
            _options.defaultOption = select.data().defaultOption ? select.data().defaultOption : _options.defaultOption;
            _options.valueName = select.data().valueName ? select.data().valueName : _options.valueName;
            _options.showName = select.data().showName ? select.data().showName : _options.showName;
            _options.selectName = select.data().selectName ? select.data().selectName : _options.selectName;
            if(_options.defaultOption) txt += '<option value="">'+_options.defaultOption+'</option>';
            for(let i in data) {
                let item = data[i];
                let code = item.code == undefined ? '' : item.code;

                let dataAttr = '';
                let selected = '';
                for(let j in item) {
                    if(j == _options.valueName || j == _options.showName) continue;
                    let attr = j.replace(/[A-Z]/g, function re(x) {
                        return '-'+x.toLowerCase();
                    });

                    if(typeof item[j] == "string"){
                        item[j] = item[j].replaceAll("\"", "'");
                    }

                    dataAttr += 'data-' + attr + '="' + item[j] + '" ';
                }

                txt += '<option value="'+item[_options.valueName]+'" '+dataAttr+'>'+item[_options.showName]+'</option>';
            }
            if(_options.useAppend) select.append(txt);
            else select.empty().append(txt);
            if(_options.beforeRefresh != undefined && $.isFunction(_options.beforeRefresh)) _options.beforeRefresh.call(this, data, select);

            if(select.data().selected) {
                select.val(select.data().selected);
                if(select.find("option:selected").length == 0){
                    select.val("");
                }
                setTimeout(function(){
                    select.trigger("change");
                }, 150);
            }

            let t = $(select.data().afterTargetJquerySelector);
            if(t){
                t.data("data", {});
                t.selectAjax();
            }

            if(_options.success != undefined && $.isFunction(_options.success)) _options.success.call(this, data, select);
            return this;
        },
        init: function(options) {
            let defaults = {
                useAppend: false,
                valueName: "cd",
                showName: "cdNm"
            };
            this.each(function() {
                let select = $(this);
                let _options = $.extend(true, {}, defaults);
                _options = $.extend(true, _options, options);
                _options = $.extend(true, _options, select.data());
                select.data("_options", _options);
                if(_options.jsonData) {
                    selectBoxActions.render.call(select, _options.jsonData);
                } else {
                    let opt = select.data();
                    let url = (_options.url != undefined) ? _options.url : opt.url;
                    let data = $.extend(true, _options.data, opt.data);
                    if(url != undefined && url != "") {
                        $.ajax({
                            url: url,
                            data: data,
                            type: opt.type,
                            success: function(data) {
                                selectBoxActions.render.call(select, data.data);
                            }
                        });
                    }
                }
            });
            return this;
        }
    };
    $.fn.selectAjax = function(action) {
        if(selectBoxActions[action]) {
            return selectBoxActions[action].apply(this, Array.prototype.slice.call(arguments, 1));
        } else {
            return selectBoxActions.init.apply(this, arguments);
        }
    };
})($);

$(document).on("change", ".selectajax", function(){
    let self = $(this);
    let selector = self.data("afterTargetJquerySelector");
    let data = {};
    data[self.data("afterTargetParameterName")] = self.val();
    $(selector).data("data", data);
    $(selector).selectAjax();
});
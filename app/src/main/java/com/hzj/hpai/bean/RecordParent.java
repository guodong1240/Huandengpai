package com.hzj.hpai.bean;

import java.util.List;

/**
 * Created by zx on 2017/1/5.
 */

public class RecordParent extends BaseBean {
    private List<RecordData> datas;

    public List<RecordData> getDatas() {
        return datas;
    }

    public void setDatas(List<RecordData> datas) {
        this.datas = datas;
    }

    public class RecordData extends BaseBean {
        private RecordeBean data;

        public RecordeBean getData() {
            return data;
        }

        public void setData(RecordeBean data) {
            this.data = data;
        }
    }
}

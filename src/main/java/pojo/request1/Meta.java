
package pojo.request1;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Meta {

    @SerializedName("pagination")
    private Pagination mPagination;

    public Pagination getPagination() {
        return mPagination;
    }

    public void setPagination(Pagination pagination) {
        mPagination = pagination;
    }

}

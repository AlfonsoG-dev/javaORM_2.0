package ORM.Utils.Formats;

import java.sql.ResultSet;

import ORM.Utils.Model.ModelMetadata;

public interface UsableMethods {

    public<T> T build(ResultSet rst);
    public default String getInstanceData() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getInstanceData(this);
    }

    public default String initModel() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getProperties();
    }
}

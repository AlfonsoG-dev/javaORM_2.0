package ORM.Utils.Formats;

import ORM.Utils.Model.ModelMetadata;

public interface UsableMethods {

    public default String getInstanceData() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getInstanceData(this);
    }

    public default String initModel() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getProperties();
    }
}

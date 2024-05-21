package ORM.Utils.Formats;

import ORM.Utils.Model.ModelMetadata;

public interface UsableModel {

    public String getInstanceData();

    public default String initModel() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getModelProperties();
    }
}

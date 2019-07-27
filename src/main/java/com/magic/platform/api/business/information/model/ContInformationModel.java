package com.magic.platform.api.business.information.model;

import com.magic.platform.entity.mapper.build.entity.ContAssociation;
import com.magic.platform.entity.mapper.build.entity.ContInformation;
import java.util.List;
import lombok.Data;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-07-27 00:00
 * @Modified By:
 */
@Data
public class ContInformationModel extends ContInformation {

  private List<ContAssociation> information;
  private List<ContAssociation> labels;
}

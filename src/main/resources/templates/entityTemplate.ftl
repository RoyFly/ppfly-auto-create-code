package ${package}.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author ${author}
 * @time: ${now}
 * @description: ${tableNameComments}
 */
@Data
@Entity
@Table(name = "${tableName}")
public class ${entityName} implements Serializable {
	${createPropStr}
}

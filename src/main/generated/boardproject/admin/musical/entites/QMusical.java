package boardproject.admin.musical.entites;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMusical is a Querydsl query type for Musical
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMusical extends EntityPathBase<Musical> {

    private static final long serialVersionUID = 1586015694L;

    public static final QMusical musical = new QMusical("musical");

    public final boardproject.commons.entities.QBaseMember _super = new boardproject.commons.entities.QBaseMember(this);

    public final BooleanPath active = createBoolean("active");

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath eDate = createString("eDate");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath mTitle = createString("mTitle");

    public final StringPath sDate = createString("sDate");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QMusical(String variable) {
        super(Musical.class, forVariable(variable));
    }

    public QMusical(Path<? extends Musical> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMusical(PathMetadata metadata) {
        super(Musical.class, metadata);
    }

}


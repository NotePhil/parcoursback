package cmr.notep.repository;

import cmr.notep.dao.TokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokensRepository extends JpaRepository<TokensEntity,String> {
}

@Configuration
public class DomaConfig implements org.seasar.doma.jdbc.Config {

  @Autowired
  private DataSource dataSource;

  private final Dialect dialect = new PostgresDialect();

  @Override
  public DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public Dialect getDialect() {
    return dialect;
  }

  @Override
  public SqlFileRepository getSqlFileRepository() {
    return new GreedyCacheSqlFileRepository();
  }

  // その他必要に応じて TransactionManager なども定義
}
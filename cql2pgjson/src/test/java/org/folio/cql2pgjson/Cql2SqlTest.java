package org.folio.cql2pgjson;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.folio.cql2pgjson.exception.QueryValidationException;
import org.folio.okapi.common.URLDecoder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class Cql2SqlTest {

  private static CQL2PgJSON cql2pg;

  @BeforeClass
  public static void runBeforeClass() throws Exception {
    cql2pg = new CQL2PgJSON("users.jsonb");
  }

  static void cql2sqlWhere(String testcase) {
    int hash = testcase.indexOf(':');
    Assert.assertTrue("hash character in testcase", hash >= 0);
    String cql = testcase.substring(0, hash).trim();
    String expectedWhereClause = URLDecoder.decode(testcase.substring(hash + 1).trim());
    
    
    String gotWhereClause = "Unknown error";
    try {
      gotWhereClause = cql2pg.toSql(cql).getWhere();
    } catch (QueryValidationException ex) {
      gotWhereClause = "Exception:" + ex.getMessage();
    }
    Assert.assertEquals(expectedWhereClause, gotWhereClause);
  }

  @Test
  @Parameters({
    "name=a : to_tsvector('simple'%2C users.jsonb->>'name') @@ replace((to_tsquery('simple'%2C ('''a''')))::text%2C '&'%2C '<->')::tsquery",
    "contributors =/@lang=dk a : "
      + "to_tsvector('simple'%2C users.jsonb->>'contributors') @@ "+"replace((to_tsquery('simple'%2C ('''a''')))::text%2C '&'%2C '<->')::tsquery AND id in (select t.id from (select id as id%2C"
      +"              jsonb_array_elements(users.jsonb->'contributors') as c      ) as t where to_tsvector('simple'%2C t.c->>'name') @@ "
      +"replace((to_tsquery('simple'%2C ('''a''')))::text%2C '&'%2C '<->')::tsquery and to_tsvector('simple'%2C t.c->>'lang') @@ replace((to_tsquery('simple'%2C ('''dk''')))::text%2C '&'%2C '<->')::tsquery)",
    "identifiers = /@identifierTypeId=1234 a : "
      + "to_tsvector('simple'%2C f_unaccent(users.jsonb->>'identifiers')) @@ "
      +"replace((to_tsquery('simple'%2C f_unaccent('''a''')))::text%2C '&'%2C '<->')::tsquery AND id in (select t.id from (select id as id%2C"
      +"              jsonb_array_elements(users.jsonb->'identifiers') as c      ) as t where to_tsvector('simple'%2C f_unaccent(t.c->>'value'))"
      +" @@ replace((to_tsquery('simple'%2C f_unaccent('''a''')))::text%2C '&'%2C '<->')::tsquery and "
      +"to_tsvector('simple'%2C f_unaccent(t.c->>'identifierTypeId')) @@ replace((to_tsquery('simple'%2C f_unaccent('''1234''')))::text%2C '&'%2C '<->')::tsquery)",
    "name==a : lower(f_unaccent(users.jsonb->>'name')) LIKE lower(f_unaccent('a'))"
  })
  public void testWhere(String testcase) {
    cql2sqlWhere(testcase);
  }

}

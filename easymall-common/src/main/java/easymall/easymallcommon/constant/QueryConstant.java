package easymall.easymallcommon.constant;

/**
 * @Description:
 * @Name: QueryConstant
 * @Author peipei
 * @Date 2022/3/27
 */
public class QueryConstant {
    public enum Order{
        ASC("asc"),
        DESC("desc");

        private String order;

        public String getOrder() {
            return order;
        }

        Order(String order) {
            this.order = order;
        }
    }


}

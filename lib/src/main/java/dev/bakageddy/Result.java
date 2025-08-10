public class Result<Key, Value> {
	enum {
		OK,
		ERR
	} result_tag;

	Key key;
	Value val;
}

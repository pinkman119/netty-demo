package Buffer测试;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/9/28 19:52
 * @Tips:
 */
@Slf4j
public class 拆包和粘包 {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,Word\nI am Goods".getBytes(StandardCharsets.UTF_8));
        // 切换写模式
        source.flip();
        ArrayList<ByteBuffer> arrayList = new ArrayList<>();
        for (int i = 0; i < source.limit(); i++) {
            log.debug(String.valueOf((char) source.get()));
            if (source.get(i) =='\n') {
                int len = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(len);
                for (int j = 0; j < len;j++) {
                    target.put(source.get());
                }
                arrayList.add(target);
            }
        }
    }
}

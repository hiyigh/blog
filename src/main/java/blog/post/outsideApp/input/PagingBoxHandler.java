package blog.post.outsideApp.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingBoxHandler {

    private int startPageNum = 1;
    private int curPageNum;
    private int lastPageNum;

    private int listStartNum;
    private int listEndNum;

    // 출력할 페이지 개수
    private final int displayPageBoxCnt = 5;
    // 페이지당 글의 개수
    private final int displayPostPerPage = 5;
    public static PagingBoxHandler create(int page, int totalPosts) {

        PagingBoxHandler box = new PagingBoxHandler();
        // 현재 페이지
        box.curPageNum = page;
        box.lastPageNum = (int) (Math.ceil(totalPosts / (double) box.displayPostPerPage));

        // 에러 핸들링
        if(box.curPageNum > box.lastPageNum){
            box.curPageNum = box.lastPageNum;
        }

        if(box.curPageNum <= 0){
            box.curPageNum = 1;
        }

        // list 시작 번호 계산
        if(box.curPageNum % box.displayPageBoxCnt == 0) {
            box.listStartNum = ((box.curPageNum / box.displayPageBoxCnt)-1) * box.displayPageBoxCnt + 1;
        }else {
            box.listStartNum = (box.curPageNum - 1 ) * box.displayPageBoxCnt + 1;
        }

        // list 끝번호 계산
        box.listEndNum = (box.curPageNum * box.displayPageBoxCnt);

        if(box.listEndNum <= 0){
            box.listEndNum = 1;
        }
        return box;
    }
}

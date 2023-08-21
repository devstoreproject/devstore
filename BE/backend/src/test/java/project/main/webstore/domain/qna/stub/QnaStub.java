package project.main.webstore.domain.qna.stub;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class QnaStub {

    public Answer getAnswer(Long id){
        return new Answer(id,"이곳은 답변입니다."+id,1L,1L);
    }
    public Answer getAnswer(){
        return new Answer("이곳은 답변입니다.",1L,1L);
    }

    public Question getQna(Long id){
        return new Question(id,"멘션"+id,1L,1L);
    }
    public Page<Question> getQnaPage(Long id){
        PageRequest page = PageRequest.of(0, 30);
        List<Question> qnaList = getQnaList(id);
        return new PageImpl<Question>(qnaList,page,id+1);
    }

    private List<Question> getQnaList(Long index) {
        List<Question> list =  new ArrayList<Question>();
        for(long i = 1; i <= index ; i++){
            list.add(getQna(i));
        }
        return list;
    }


}

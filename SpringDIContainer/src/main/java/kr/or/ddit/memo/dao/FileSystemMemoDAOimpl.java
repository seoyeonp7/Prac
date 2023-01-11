package kr.or.ddit.memo.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.MemoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FileSystemMemoDAOimpl implements MemoDAO{
	@Inject
	private ApplicationContext context;
	
	//init : 생성자가 생성되고 모든 인젝션이 끝난 뒤 수행됨
	@PostConstruct
	public void init() {
		dataBase = context.getResource("file:d:/memos.dat");
		log.info("리소스 로딩:{}", dataBase);
		//리소스 찾은 후 수행
		try(
			InputStream fis = dataBase.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);
		){
			memoTable = (Map<Integer, MemoVO>) ois.readObject();
		}catch (Exception e) {
			System.err.println(e.getMessage());
			this.memoTable = new HashMap<>();
		}
	}
	
//	private File dataBase = new File("d:/memo.dat"); // resource api 사용
	private Resource dataBase; //container가 그 자체로 resource loader가 된다.
	private Map<Integer, MemoVO> memoTable; // 직렬화 역직렬화 대상
	
	@Override
	public List<MemoVO> selectMemoList() {
		return new ArrayList<>(memoTable.values());
	}

	@Override
	public int insertMemo(MemoVO memo) {
		int maxCode = memoTable.keySet().stream()
									.mapToInt(key->key.intValue())
									.max()
									.orElse(0);
		
		memo.setCode(maxCode+1);
		memoTable.put(memo.getCode(), memo);
		serializeMemoTable();
		return 1;
	}
	private void serializeMemoTable() {
		try(
			FileOutputStream fos = new FileOutputStream(dataBase.getFile());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			oos.writeObject(memoTable);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateMemo(MemoVO memo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMemo(int code) {
		// TODO Auto-generated method stub
		return 0;
	}
}

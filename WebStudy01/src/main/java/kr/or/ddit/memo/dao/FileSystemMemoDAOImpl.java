package kr.or.ddit.memo.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.MemoVO;

public class FileSystemMemoDAOImpl implements MemoDAO {
	private static FileSystemMemoDAOImpl instance;
	public static FileSystemMemoDAOImpl getInstance() {
		if(instance==null)
			instance = new FileSystemMemoDAOImpl();
		return instance;
	}
	
	private File dataBase = new File("d:/memos.dat");
	private Map<Integer, MemoVO> memoTable;
	
	private FileSystemMemoDAOImpl() {
		//역직렬화를 통해 데이터 복원
		try(
			FileInputStream fis = new FileInputStream(dataBase);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);
		){
			memoTable = (Map<Integer, MemoVO>) ois.readObject();
		} catch(Exception e) {
			System.err.println(e.getMessage());
			this.memoTable = new HashMap<>();
		}
	}
	
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
		memoTable.put(memo.getCode(),memo);
		//직렬화
		serializeMemoTable();
		return 1;
	}
	
	private void serializeMemoTable() {
		try(
			FileOutputStream fos = new FileOutputStream(dataBase);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			oos.writeObject(memoTable);
		}catch(Exception e) {
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

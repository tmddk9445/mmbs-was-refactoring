package com.mong.mmbs.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mong.mmbs.common.constant.ResponseMessage;
import com.mong.mmbs.dto.request.ask.AskPatchRequestDto;
import com.mong.mmbs.dto.request.ask.AskPostRequestDto;
import com.mong.mmbs.dto.response.ResponseDto;
import com.mong.mmbs.dto.response.ask.AskGetListResponseDto;
import com.mong.mmbs.dto.response.ask.AskDeleteResponseDto;
import com.mong.mmbs.dto.response.ask.AskGetAskIdResponseDto;
import com.mong.mmbs.dto.response.ask.AskGetFindResponseDto;
import com.mong.mmbs.dto.response.ask.AskPatchResponseDto;
import com.mong.mmbs.dto.response.ask.AskPostResponseDto;
import com.mong.mmbs.repository.AskRepository;
import com.mong.mmbs.entity.AskEntity;

@Service
public class AskService {

  @Autowired AskRepository askRepository;

  // 문의 작성
  public ResponseDto<AskPostResponseDto> post(AskPostRequestDto dto){

    AskPostResponseDto data = null;

    AskEntity askEntity = null;

		try {

      askEntity = new AskEntity(dto);
			askRepository.save(askEntity);

      data = new AskPostResponseDto(askEntity);

		} catch (Exception exception) {
      exception.printStackTrace();
			return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
		}

	  return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

	}

  // 문의 리스트 출력
  public ResponseDto<AskGetListResponseDto> getList(String userId) {

	  AskGetListResponseDto data = null;

    List<AskEntity> askList = new ArrayList<AskEntity>();

		try {

			askList = askRepository.findByAskWriter(userId);

      data = new AskGetListResponseDto(askList);

		} catch(Exception exception){
      exception.printStackTrace();
			return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
		}

		return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

	}
	
  // 수정할 문의 출력
	public ResponseDto<AskGetAskIdResponseDto> get(int askId){
		
    AskGetAskIdResponseDto data = null;

    AskEntity askEntity = null;

		try {

			askEntity = askRepository.findByAskId(askId);

      data = new AskGetAskIdResponseDto(askEntity);

		} catch (Exception exception) {
      exception.printStackTrace();
			return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
		}

		return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

	}

  // 문의 검색
  public ResponseDto<AskGetFindResponseDto> find(String userId, String askStatus, int months, String askSort) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = Date.from(Instant.now().minus(months * 30, ChronoUnit.DAYS));
		String askDateTime = simpleDateFormat.format(date);

    AskGetFindResponseDto data = null;

		List<AskEntity> askList = new ArrayList<AskEntity>();

		try {

			askList = askRepository.findByAskWriterAndAskDatetimeGreaterThanEqualAndAskSortContainsAndAskStatusContainsOrderByAskDatetimeDesc(userId, askDateTime, askSort, askStatus);
      data = new AskGetFindResponseDto(askList);

		} catch(Exception exception){
			exception.printStackTrace();
			return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
		}

		return ResponseDto.setSuccess("Success", data);
	
	}

  // 문의 수정
	public ResponseDto<AskPatchResponseDto> patch(AskPatchRequestDto dto) {

    AskPatchResponseDto data = null;

		AskEntity askEntity = null;
		int askId = dto.getAskId();

		try {

			askEntity = askRepository.findByAskId(askId);
			if (askEntity == null) return ResponseDto.setFailed("Does Not Exist User");

      askEntity.patch(dto);
      askRepository.save(askEntity);

      data = new AskPatchResponseDto(askEntity);

		} catch (Exception exception) {
      exception.printStackTrace();
			ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
		}

		return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
		
	}
	
  // 문의 삭제
	public ResponseDto<AskDeleteResponseDto> delete(String userId, int askId){

    AskDeleteResponseDto data = null;

    List<AskEntity> askList = new ArrayList<AskEntity>();

		try {

			AskEntity askEntity = askRepository.findByAskId(askId);
			askRepository.delete(askEntity);

      askList = askRepository.findByAskWriter(userId);

      data = new AskDeleteResponseDto(askList);

		} catch (Exception exception) {
      exception.printStackTrace();
			ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
		}

		return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

	}

}

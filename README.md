# android-slide
Android 학습 프로젝트 #2

>
> ## Task 1

#### 정사각형 클래스 구현: Slide
- 랜덤한 ID 값은 Factory에서 부여

#### SlideItemFactory interface
- 이를 상속받아 SquareSlideFactory 구현
- `createSlide` 메서드를 통해 Slide 객체 생성
- `getRandomId` 랜덤 ID 값 생성

#### 결과 화면
<img width="509" alt="3-1" src="https://github.com/JoYehyun99/android-slide/assets/81362348/929d0a6f-b514-4987-a26f-5b80cf9b92d3">


> ## Task 2

#### `SlideManager` 구현
- 여러 슬라이드를 관리할 수 있도록  `wholeSlides`라는 MutableList 생성
- 슬라이드 내 도형 색상은 `ARGB` 객체를 생성하여 관리: r,g,b 색상과 alpha 값을 묶어서 저장함
- `alpha`값은 enum class를 통해 해당하는 투명도 코드를 불러올 수 있도록 설정


#### ViewModel 사용
- 현재 메인이 되는 `Slide` 객체와 슬라이드 내부의 사각형이 선택되었는지 판단하는 `isSelected`를 LiveData로 설정
- slide에 그려진 사각형의 색상 혹은 투명도의 변경은 slideManager을 통해서 이루어지도록 설정
   

#### UnitTest 작성

- TEST 1) `addSlide`를 통해 슬라이드를 추가하면 해당 슬라이드가 잘 추가가 되는지
- TEST 2) 슬라이드를 추가할 때 동일한 ID의 슬라이드가 생성되지는 않는지 
- TEST 3) 슬라이드를 추가한 만큼 전체 슬라이드 개수가 증가하는지
- TEST 4) 배경색을 변경이 잘 되는지 (이전 색깔과 다르게 반환되는지)
- TEST 5) 투명도가 1과 10사이 내에서만 잘 적용되는지 (2부터 9까지만 덧셈/뺄셈 적용 가능)
- TEST 6) 투명도가 10일 경우 11로 안 넘어가는지

#### 결과 화면

<img width="899" alt="1" src="https://github.com/JoYehyun99/android-slide/assets/81362348/e7b52c8b-4962-4e7f-9ea4-12d0925a42b5"> <img width="898" alt="2" src="https://github.com/JoYehyun99/android-slide/assets/81362348/23715671-0c2a-41c7-b31d-d52b86ac958d"> <img width="901" alt="3" src="https://github.com/JoYehyun99/android-slide/assets/81362348/4cfa173f-b3ec-476a-8703-a3da6544f6e4">   




> ## Task 3

#### `Slide` 객체 수정
- sealed class 사용
- 정사각형 슬라이드 / 이미지 슬라이드 구분해서 구현


#### 슬라이드 목록 구현
- RecyclerView 사용
- 추가 버튼을 누르면 slide list에 데이터 추가할 수 있도록 구현
- 현재 클릭된 슬라이드 아이템의 배경색 변경
- `ItemTouchHelper.Callback()` 을 활용해 드래그로 순서 변경 기능 구현


#### 수정 사항
`wholeSlides` -> `slideList` 이름 변경  
data class 변수 선언 -> `val` 사용

<img width="809" alt="dragSlide" src="https://github.com/JoYehyun99/android-slide/assets/81362348/5ed87463-b195-494c-8790-3b2e11ec56ee">


> ## Task 4

#### 슬라이드 목록 설정
- `PopupMenu` 사용하여 길게 눌렀을 때 메뉴 표시하고, 그에 맞게 순서 이동

#### `Slide` 객체 수정
- 이미지 슬라이드에서 uri를 `byteArray`로 저장하도록 설정

#### 사진 불러오기 기능
- `Phopto Picker` - `PickVisualMedia` 사용
- `Glide` 이용하여 `byteArray` 형식으로 전환
- `imageAlpha` 사용하여 이미지 투명도 설정

#### 결과 화면

![3_5](https://github.com/JoYehyun99/android-slide/assets/81362348/5b8e669b-28a1-4051-9f0e-84421f78f209)


> ## Task 5

#### JSON 데이터 가져오기
- `Retrofit2`을 사용하여 JSON 데이터 불러오기
- 전체적인 데이터 구조 수정
  : `ARGB`로 `alpha` 값과 `color`값을 한번에 관리 -> `RGB`와 `alpha`값 분리하여 저장
  : `ImageSlide` 와 `SquareSlide` 변수 재설정


 #### Data Binding
 - Task 5 기능까지 구현 완료 후 Data Binding을 사용하도록 전체적인 코드 수정


#### 결과 화면
<img width="868" alt="5" src="https://github.com/JoYehyun99/android-slide/assets/81362348/8e625446-cdac-4d1c-8ccc-afac2daf1d0d">


> ## Task 6

#### Drawing View 구현 
- CustomView 사용
- 한 슬라이드 당 하나의 선만 그릴 수 있음
- 선을 그리면 border로 표시


#### 결과 화면
<img width="825" alt="11" src="https://github.com/JoYehyun99/android-slide/assets/81362348/f7ceb81b-4335-4cdc-9c4b-2d94d9fa8d7b">




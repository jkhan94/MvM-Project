# ✌🏻 MvM

해당 서버는 **Retro Music 을 넘어 Metro Music 🎵 을 공유, 추천하는 뉴스피드 프로그램**입니다.<br/>
<br>

![image](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/1de525d8-50d8-42d1-bd87-6b33cd3529d0)

이 프로그램에서는 회원가입과 로그인을 통해 사용자가 게시글을 작성, 조회, 수정, 삭제할 수 있으며, 선택한 게시글에 댓글을 등록, 수정, 삭제할 수 있는 기능을 제공합니다. 또한 사용자의 프로필 조회 및 수정도 가능합니다.

해당 앱 서버는 IntelliJ를 사용하여 개발되었으며, 백엔드 데이터베이스로 MySQL을 사용합니다.

각 기능은 RESTful API를 통해 구현되었으며, 클라이언트는 HTTP 요청을 통해 서버와 상호작용합니다. 모든 사용자가 게시글을 조회할 수 있지만, 데이터의 보안을 위해 사용자 인증을 거친 후에만 게시글의 등록, 수정, 삭제가 가능하도록 설계되었습니다.


<br>

# 👨‍👦‍👦👩‍👧 Team 💖

<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/leesw1945"><img src="https://avatars.githubusercontent.com/u/67689184?v=4" width="100px;" alt=""/><br /><sub><b> 팀장 : 이세원 </b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/LeeNaYoung240"><img src="https://avatars.githubusercontent.com/u/107848521?v=4" width="100px;" alt=""/><br /><sub><b> 팀원 : 이나영 </b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/inho9979"><img src="https://avatars.githubusercontent.com/u/37062407?v=4" width="100px;" alt=""/><br /><sub><b> 팀원 : 이인호 </b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/JKhan0408/"><img src="https://avatars.githubusercontent.com/u/166383040?v=4" width="100px;" alt=""/><br /><sub><b> 팀원 : 한진경 </b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/gminnimk"><img src="https://avatars.githubusercontent.com/u/165118770?v=4" width="100px;" alt=""/><br /><sub><b> 팀원 : 김경민 </b></sub></a><br /></td>
    </tr>
  </tbody>
</table>
<br/>

<details>
<summary>이세원</summary>
<div markdown="1">

- 사용자 인증 기능: 회원가입, 회원탈퇴
- 발표

</div>
</details>


<details>
<summary>이나영</summary>
<div markdown="1">

- 뉴스피드 게시물 CRUD : 게시물 작성, 조회, 수정, 삭제
- 댓글 CRUD : 댓글 작성, 조회, 수정, 삭제
- 좋아요 CRUD : 좋아요 등록, 취소
</div>
</details>

<details>
<summary>이인호</summary>
<div markdown="1"> 
  
- 필터, 토큰 : 인증/인가
  
</div>
</details>

<details>
<summary>한진경</summary>
<div markdown="1">
  
- 프로필 관리 기능: 프로필 조회, 프로필 수정
  
</div>
</details>

<details>
<summary>김경민</summary>
<div markdown="1">
  
- 로그인, 로그아웃

</div>
</details>

<br>

# 🗳 Tech Stack

| Type       | Tech                                                                                                              | Version                                                                                                           |
| ---------- | ----------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| IDE        |  ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)     |         |
| Framework        |  ![Spring](https://img.shields.io/badge/SpringBoot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)          | 3.2.6       |
| Langage      | ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)                  | JDK17              |
| Database   | ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)       | 8.0.28      |
| Tools   | ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)  ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)       |     |


<br>

# 🌠 Features

- 사용자 인증을 할 수 있습니다.
  - 회원가입, 회원탈퇴, 로그인, 로그아웃
  
  - Spring Security와 JWT를 사용하여 회원 가입과 로그인을 진행합니다.
  	- 회원 가입 : 신규 가입자는 사용자 ID, 비밀번호를 입력하여 서비스에 가입할 수 있습니다.
     
  	- 회원 탈퇴 : 사용자의 비밀번호를 확인하여 탈퇴할 수 있습니다.
 	 - 로그인 : 사용자는 자신의 계정으로 로그인 할 수 있습니다.
  	- 로그아웃 : 사용자는 로그인 되어 있는 본인의 계정을 로그아웃 할 수 있습니다.
 
- 프로필을 관리할 수 있습니다.
  - 프로필 조회, 프로필 수정
    
 	 - 프로필 조회 : 프로필을 조회하여 사용자 ID, 이름, 한 줄 소개, 이메일을 볼 수 있습니다.
     
 	 - 프로필 수정 : 로그인한 사용자는 본인의 정보를 수정할 수 있습니다.

- 뉴스피드 게시물을 괸리할 수 있습니다.
  - 게시물 CRUD 기능
  
 	 - 모든 사용자는 게시글을 조회할 수 있고 인가된 사용자는 게시글을 작성, 수정, 삭제할 수 있습니다.
     
 	 - 게시글의 등록, 수정, 삭제는 본인만 가능합니다.
 
- 게시글의 댓글을 관리할 수 있습니다.
  - 댓글 CRUD 기능
  
 	 - 사용자는 게시글에 댓글을 작성할 수 있고 본인의 댓글만 수정 및 삭제가 가능합니다.
 
- 사용자는 게시글 및 댓글에 좋아요를 남기거나 취소할 수 있습니다.

<br>

</div>
</details>

# 📑 Technical Documentation

<details>
<summary>🧩 와이어프레임</summary>
<div markdown="1">
  

![image](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/55c7c6aa-7d67-4bb3-a028-959541489ce2)
![image](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/03ba3272-d49e-4248-aa86-75d55575d007)



</div>
</details>

</div>
</details>

<details>
<summary>🧬 ERD DIAGRAM</summary>
<div markdown="1">
  

![Untitled (2)](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/e7c3c95a-2fad-4464-8de2-d07c3f07aaeb)


</div>
</details>


<details>
<summary> 🔨 API 명세서</summary>
<div markdown="1">
  

![image](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/8086a605-3608-4f10-885a-2af78dbe1d3d)
![image](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/90171df8-ba73-4e44-aa8c-c98baf85a710)
![image](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/ca17ac83-848c-4415-bff1-339fabb27a90)
![image](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/a4435795-ea49-4f4f-9658-4fe64761a090)

</div>
</details>



<br>



</div>
</details>

<details>
<summary>🙌🏻 Git Rules</summary>
<div markdown="1">
  

![image](https://github.com/LeeNaYoung240/MvM-Project/assets/107848521/fffe7591-28e3-4211-822a-e87b5e0fc372)

기능 단위 별 브랜치 — 병합 → develop branch —최종 병합→ master branch

- 제목 (Type : subject)

(’한줄 띄어 분리’)

- 본문 (Body)

(’한줄 띄어 분리’)

### Subject

- 제목은 50글자 이내로 작성
- 첫글자는 대문자로 작성
- 마침표 및 특수기호는 사용하지 않음
- 과거시제 사용하지 않음

### Body

- 선택사항으로 모든 커밋에 본문 내용을 작성할 필요는 없음


|  작업 타입     |   작업 내용       |                                                                                                         
| ---------- | -------- | 
|   ✨ update       | 해당 파일에 새로운 기능이 생김         |
|  🎉 add      |  없던 파일을 생성함, 초기 세팅        |
|  🐛 bugfix   |      버그 수정                           |
|♻️ refactor  | 코드 리팩토링          |
| 🩹 fix  |     코드 수정      |
|🔥 del  |    기능/파일을 삭제      |
|🔨script  |    	package.json 변경(npm 설치 등)      |



</div>
</details>

<details>
<summary>🥨 Code Convention</summary>
<div markdown="1">
  
|   종류    |   설명       | 표기법       |
| ---------- | ---------- | ---------- |
|  변수      | 영문/숫자만을 사용해야 하고 카멜 표기법을 따라야 함. |  camel case       |
|    상수    |   단어 간 구분을 위해 언더스코어(_)를 사용해야 함. 또한 대문자로만 표기해야 함.         |  scream snake case    |
|   패키지  |   패키지 이름은 소문자로만 구성되어야 함. 언더스코어(_)의 사용도 금지됨.                |   small letter            |
| 클래스 | 식별자 첫 단어는 대문자로 지정되고 서로 상이한 단어들을 구분 짓기 위해서 각단어의 시작을 대문자로 작성      |  upper camel case    |
| 메서드  | 이름은 동사로 시작해야함. 조회 메서드에는 where 절에 들어갈 파라미터명을 By 뒤에 써주면 가독성이 올라감.      |lower camel case     |
| boolean  |  앞에 is를 붙이고 파스칼 케이스를 적용함.     | 예시) isTrue    |
| interface  |       | upper camel case    |


</div>
</details>


<br>

# 🔦 추가

[![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)](https://teamsparta.notion.site/7-c0c2778e462a4bf297c1d11fb965c180)

<br>

# 📸 video





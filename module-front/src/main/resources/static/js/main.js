$(document).ready(function () {
  //club-visual
  const swiper = new Swiper(".club-visual-swiper", {
    // Optional parameters
    effect: "fade",
    loop: true,
    speed: 600,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false,
    },
    on: {
      slideChangeTransitionStart: function () {
        // Progress bar reset and start animation from 0%
        $(".club-visual-swiper .progress").stop(true, true).css("height", "0%").animate({ height: "100%" }, 5000);
      },
    },
  });

  $(".club-visual-swiper .progress").animate({ height: "100%" }, 5000);

  //club-info
  const holeSwiper = new Swiper(".hole .swiper", {
    breakpoints: {
      0: {
        slidesPerView: "auto",
        spaceBetween: 8,
        freeMode: true,
      },
      992: {
        slidesPerView: 4,
        spaceBetween: 24,
      },
      1200: {
        slidesPerView: 5,
        spaceBetween: 40,
      },
    },
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
  });
  const clubhouseSwiper = new Swiper(".clubhouse .swiper", {
    breakpoints: {
      0: {
        slidesPerView: "auto",
        spaceBetween: 8,
        freeMode: true,
      },
      992: {
        slidesPerView: 4,
        spaceBetween: 24,
      },
      1200: {
        slidesPerView: 5,
        spaceBetween: 40,
      },
    },
    navigation: {
      nextEl: ".swiper-button-next",
      prevEl: ".swiper-button-prev",
    },
  });
  const mealSwiper = new Swiper("#amenity-meal", {
    breakpoints: {
      0: {
        slidesPerView: "auto",
        spaceBetween: 8,
        freeMode: true,
      },
      768: {
        slidesPerView: 2,
        spaceBetween: 16,
      },
    },
    navigation: {
      nextEl: ".swiper-button-next-meal",
      prevEl: ".swiper-button-prev-meal",
    },
  });
  const lockerSwiper = new Swiper("#amenity-locker", {
    breakpoints: {
      0: {
        slidesPerView: "auto",
        spaceBetween: 8,
        freeMode: true,
      },
      768: {
        slidesPerView: 2,
        spaceBetween: 16,
      },
    },
    navigation: {
      nextEl: ".swiper-button-next-locker",
      prevEl: ".swiper-button-prev-locker",
    },
  });

  //hotel-info
  $(".hotel-info .hotel-info-summary .nav-link").on("click", function (e) {
    e.preventDefault();

    var target = $(this).data("target");

    $(".hotel-info .hotel-info-summary .nav-link").removeClass("active");
    $(".hotel-info .tab-content-1 > .tab-pane").removeClass("active");
    $(".hotel-info .tab-content-2 > .tab-pane").removeClass("active");
    $(".hotel-info .bg").removeClass("active");

    // 클릭된 탭 활성화
    $(this).addClass("active");

    // 첫 번째 탭 콘텐츠 활성화
    $(".hotel-info .tab-content-1 ." + target + "-pane").addClass("active");

    // 두 번째 탭 콘텐츠 활성화
    $(".hotel-info .tab-content-2 ." + target + "-pane").addClass("active");

    //배경이미지 전환
    $("#" + target + "-bg").addClass("active");
  });

  //tour-reviews
  let swiperPlace1;
  let swiperPlace2;
  let swiperReviews;

  function initSwiperPlace() {
    swiperPlace1 = new Swiper("#place-restaurant .swiper", {
      slidesPerView: 1,
      spaceBetween: 16,
      centeredSlides: true,
      breakpoints: {
        768: {
          slidesPerView: 2,
          spaceBetween: 20,
          centeredSlides: false,
        },
      },
    });
    swiperPlace2 = new Swiper("#place-tour .swiper", {
      slidesPerView: 1,
      spaceBetween: 16,
      centeredSlides: true,
      breakpoints: {
        768: {
          slidesPerView: 2,
          spaceBetween: 20,
          centeredSlides: false,
        },
      },
    });
  }

  function destroySwiperPlace() {
    if (swiperPlace1) {
      swiperPlace1.destroy(true, true);
      swiperPlace1 = null;
    }
    if (swiperPlace2) {
      swiperPlace2.destroy(true, true);
      swiperPlace2 = null;
    }
  }

  function initSwiperReviews() {
    swiperReviews = new Swiper(".reviews .swiper", {
      slidesPerView: 1.12,
      spaceBetween: 8,
      centeredSlides: true,
      breakpoints: {
        540: {
          slidesPerView: 1.5,
          spaceBetween: 12,
          centeredSlides: false,
          slidesOffsetBefore: 20,
          slidesOffsetAfter: 20,
          freeMode: true,
        },
        768: {
          slidesPerView: 2.5,
          spaceBetween: 12,
          centeredSlides: false,
          slidesOffsetBefore: 20,
          slidesOffsetAfter: 20,
        },
      },
    });
  }

  function destroySwiperReviews() {
    if (swiperReviews) {
      swiperReviews.destroy(true, true);
      swiperReviews = null;
    }
  }

  function checkScreenWidth() {
    if (window.innerWidth <= 991) {
      if (!swiperReviews) {
        initSwiperReviews(); // 리뷰 Swiper 초기화
      }
      if (!swiperPlace1) {
        initSwiperPlace(); // 맛집 Swiper 초기화
      }
      if (!swiperPlace2) {
        initSwiperPlace(); // 관광지 Swiper 초기화
      }
    } else {
      destroySwiperReviews(); // 리뷰 Swiper 파괴
      destroySwiperPlace(); // 맛집,관광지 Swiper 파괴
    }
  }

  // 초기 화면 로딩 시 Swiper 체크
  checkScreenWidth();

  // 화면 크기 변경 시 Swiper 재설정
  window.addEventListener("resize", checkScreenWidth);
});

gsap.registerPlugin(ScrollTrigger);

$(document).ready(function () {
    let swiper;

    function initSwiper() {
        if (window.innerWidth <= 996) {
            if (!swiper) {
                // Swiper가 없을 때만 초기화
                swiper = new Swiper(".section-platform .swiper", {
                    slidesPerView: 1.2,
                    spaceBetween: 12,
                    centeredSlides: true,
                    breakpoints: {
                        768: {
                            slidesPerView: 2.2,
                            spaceBetween: 20,
                            centeredSlides: false,
                        },
                    },
                });
            }
        } else {
            if (swiper) {
                // Swiper가 존재하면
                swiper.destroy(true, true); // Swiper 인스턴스 파괴
                swiper = undefined; // swiper 변수를 초기화
            }
        }
    }

    // 디바운스 함수 추가 (resize 이벤트 성능 최적화)
    function debounce(func, wait = 100) {
        let timeout;
        return function (...args) {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), wait);
        };
    }

    // 페이지 로드 시 Swiper 초기화 체크
    $(window).on("load", initSwiper);

    // 창 크기 변경 시 Swiper 초기화 체크 (디바운스 적용)
    $(window).on("resize", debounce(initSwiper));

    const swiper2 = new Swiper(".section-partners .swiper", {
        slidesPerView: 2.5,
        spaceBetween: 0,
        autoplay: {
            delay: 0,
            disableOnInteraction: false,
            pauseOnMouseEnter: false,
        },
        loop: true,
        speed: 3000,
        breakpoints: {
            992: {
                slidesPerView: 7.5,
                spaceBetween: 24,
            },
        },
    });

    const swiper3 = new Swiper(".swiper-services-1", {
        loop: true,
        spaceBetween: 10,
        slidesPerView: 4,
        freeMode: true,
        watchSlidesProgress: true,
    });
    const swiper4 = new Swiper(".swiper-services-2", {
        slidesPerView: 1,
        loop: true,
        spaceBetween: 10,
        effect: "fade",
        autoplay: {
            delay: 2500,
            disableOnInteraction: false,
        },
        speed: 200,
        thumbs: {
            swiper: swiper3,
        },
    });

    swiper4.autoplay.stop();

    gsap.to(".section-services", {
        scrollTrigger: {
            trigger: ".section-services",
            scrub: false,
            start: "top center",
            onEnter: () => {
                swiper4.autoplay.start();
            },
            onLeave: () => {
                swiper4.autoplay.stop();
            },
            onEnterBack: () => {
                swiper4.autoplay.start();
            },
            onLeaveBack: () => {
                swiper4.autoplay.stop();
            },
        },
    });
});
